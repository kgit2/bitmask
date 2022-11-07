import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.kgit2.bitmask.BitmaskAbleVisitor
import com.kgit2.bitmask.BitmaskProcessor
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

class BitmaskProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val application = startKoin {
            printLogger(Level.DEBUG)
            modules(
                defaultModule,
                module {
                    single { environment }
                },
            )
        }
        return application.koin.get<BitmaskProcessor>()
    }
}
