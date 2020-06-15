import com.bhanna.bugtracker.notes.Note
import com.bhanna.bugtracker.notes.NotesRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(val repository: NotesRepository) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        println("THIS LINE SHOULD BE PRINTING TO THE CONSOLE")
        listOf("Note 1", "Note 2", "Note 3").forEach {
            repository.save(Note(title = it, user = "user"))
        }
        repository.findAll().forEach { println(it) }
    }
}