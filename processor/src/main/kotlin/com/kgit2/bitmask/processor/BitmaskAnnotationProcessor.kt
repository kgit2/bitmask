package com.kgit2.bitmask.processor

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.kgit2.annotation.Bitmask
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.visitor.BitmaskAnnotatedVisitor
import freemarker.template.Configuration
import koin
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import java.io.DataOutputStream
import java.io.FileWriter
import java.io.RandomAccessFile
import java.io.Writer

@Factory
@ComponentScan
class BitmaskAnnotationProcessor(
    override val environment: SymbolProcessorEnvironment,
    val configuration: Configuration,
) : SymbolProcessor, ProcessorBase {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Bitmask::class.qualifiedName!!)
        val visitor = koin.get<BitmaskAnnotatedVisitor>()
        symbols.forEach { symbol ->
            val model = symbol.accept(visitor, null)
            if (model != null) {
                val existFile = codeGenerator.generatedFile.find { it.name == "${model.fileName}.${model.fileExt}" }
                val writer = if (existFile != null) {
                    val file = RandomAccessFile(existFile, "rw")
                    file.seek(file.length())
                    object : Writer() {
                        override fun flush() {
                        }

                        override fun write(cbuf: CharArray, off: Int, len: Int) {
                            file.write(String(cbuf).toByteArray(), off, len)
                        }

                        override fun close() {
                            file.close()
                        }
                    }
                } else {
                    codeGenerator.createNewFile(
                        Dependencies(true, symbol.containingFile!!),
                        model.packageName,
                        model.fileName,
                        model.fileExt
                    ).writer()
                }
                val template = configuration.getTemplate("extension.ftl")
                template.process(model, writer)
            }
        }
        return emptyList()
    }

}
