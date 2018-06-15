# back-end-assessment
Transactions Command Processor

You can generate a shell script to run the program that will be placed on the target folder of the project.

- mvn clean install

- /.assessment 123 '{ “amount”: 1.23, “description”: “Joes Tacos”, “date”:”2018-12-30”, “user_id”: 345 }'

Plase notice that you need to scape the json object in order to pass it as an argument to the application otherwise it will not recognize the command.
