pipeline {
  agent any
  stages {
    stage('preparation') {
      parallel {
        stage('preparation') {
          steps {
            echo 'hello'
          }
        }

        stage('error') {
          steps {
            echo 'hello 2'
          }
        }

      }
    }

    stage('stage1') {
      steps {
        mail(subject: 'test', body: 'test', to: 'yohan.wahyudi@visionet.co.id')
      }
    }

  }
}