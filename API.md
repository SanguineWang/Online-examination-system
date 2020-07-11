## Student Controller

GET
/api/students/exam
获取考试列表

```json
{
  "msg": "查询成功",
  "data": {
    "length": 1,
    "examList": [
      {
        "id": 1,
        "startTime": "2020-07-11T16:34:35",
        "endTime": "2020-07-11T19:34:31",
        "name": "考试1",
        "studentName": "Default_student",
        "studentNumber": 2007,
        "teacherName": "Default_teacher",
        "teacherNumber": 3001
      }
    ]
  }
}
```

GET
/api/students/exam/{eid}
获取指定考试试卷(进入考试)

Parameters:2

```json
{
  "msg": "查询成功",
  "data": {
    "exam": {
      "name": "考试2",
      "startTime": "2020-07-11T17:36:45",
      "endTime": "2020-07-11T18:36:48",
      "choiceList": [
        {
          "id": 2,
          "title": "选择题描述1",
          "option_A": "选项A",
          "option_B": "选项B",
          "option_C": "选项C",
          "option_D": "选项D",
          "score": 2
        },
        {
          "id": 3,
          "title": "选择题描述2",
          "option_A": "选项A",
          "option_B": "选项B",
          "option_C": "选项C",
          "option_D": "选项D",
          "score": 2
        }
      ],
      "judgmentList": [
        {
          "id": 2,
          "title": "判断题描述2",
          "score": 2
        }
      ],
      "subjectiveList": [
        {
          "id": 2,
          "title": "主观题描述1",
          "score": 5
        }
      ]
    }
  }
}
```

POST
/api/students/exam/{eid}
提交考试（上传答题卡）

Parameters:2

Request Body
```json
{
    "name": "考试1",
    "startTime": "2020-07-10T08:10:41",
    "endTime": "2020-07-11T21:10:50",
    "choiceList": [
        {
            "id": 2,
            "answer":1
        },
        {
            "id": 3,
            "answer":1
        }
    ],
    "judgmentList": [
        {
            "id": 2,
            "answer":1
        }
    ],
    "subjectiveList": [
        {
            "id": 2,
            "answer":"这是主观题的回答"
        }
    ]
}
```
	
Response body
```json
{
  "msg": "上传成功",
  "data": {
    "examList": [
      {}
    ]
  }
}
```
GET
/api/students/myGrade
查看我的成绩,返回各次考试的成绩

	
Response Body
```json
{
  "msg": "查询成功",
  "data": {
    "length": 2,
    "examList": [
      {
        "id": 1,
        "startTime": "2020-07-11T16:34:35",
        "endTime": "2020-07-11T19:34:31",
        "name": "考试1",
        "studentName": "Default_student",
        "objectiveGrade": 4,
        "subjectiveGrade": 4,
        "studentNumber": 2007,
        "teacherName": "Default_teacher",
        "teacherNumber": 3001
      },
      {
        "id": 2,
        "startTime": "2020-07-11T17:36:45",
        "endTime": "2020-07-11T18:36:48",
        "name": "考试2",
        "studentName": "Default_student",
        "objectiveGrade": 6,
        "subjectiveGrade": 5,
        "studentNumber": 2007,
        "teacherName": "Default_teacher",
        "teacherNumber": 3001
      }
    ]
  }
}
```

GET
/api/students/myInfo
查询个人信息
```json
{
  "msg": "查询个人信息成功",
  "data": {
    "myInfo": {
      "user": {
        "name": "Default_student",
        "number": 2007,
        "insertTime": "2020-07-11T16:33:59"
      }
    }
  }
}
```

PATCH
/api/students/myInfo
修改个人信息
Request Body
```json
{
  "user": {
    "name": "string"
  }
}
```

	
Response body
```json
{
  "msg": "修改个人信息成功",
  "data": {
    "myInfo": {
      "user": {
        "name": "string",
        "number": 2007,
        "insertTime": "2020-07-11T16:33:59"
      }
    }
  }
}
```
