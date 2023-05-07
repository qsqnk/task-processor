# task-processor

Simple task-processor based on task polling

Create class that implements `TaskRunner` interface and annotate it with `@Component`
```
@Component
class AbcRunner : TaskRunner {
    override val name = "Abc"

    override fun run(context: TaskContext) {
        // do something
    }
}
```

Create task and submit it using `TaskService`
```
val task = Task.Builder(runnerName = "Abc")
    .withProperty("a", TextNode("aValue"))
    .withProperty("b", LongNode(1))
    .build()
taskService.submit(
    task,
    delay = Duration.ZERO,
)
```