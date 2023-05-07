# Task processor

This repository contains a simple persistent PostgreSQL-based task processor with RESTful API.

## Table of Contents

- [Endpoints](#endpoints)
  - [Submit Task](#submit-task)
  - [Get Task Info](#get-task-info)
- [Usage](#usage)
  - [Creating runner](#creating-runner)
  - [Submitting task](#submitting-task)

## Endpoints

### Submit Task

```
POST /api/v1/task
```
**Request Body**

```json
{
  "runnerName": "Abc",
  "properties": {
      "a": "aValue",
      "b": 1
  }
}
```

**Response Body**

```json
{
  "taskId": 1,
}
```

### Get Task Info

```
GET /api/v1/task/{taskId}/info
```

**Path Parameters**

- `taskId` - ID of the task to get information about

**Response Body**

```json
{
  "status": COMPLETED
}
```

## Usage

### Creating runner

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

### Submitting task

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
