pipeline {
  agent any
  stages {
    stage('preparation') {
      steps {
        dir(path: 'GradleDyReports') {
          sh '''run:
      name: chmod permissions
      command: chmod +x ./gradlew'''
        }

      }
    }

  }
}