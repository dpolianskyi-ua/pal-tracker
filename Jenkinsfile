#!groovy
import groovy.json.JsonSlurperClassic

if (env.BRANCH_NAME == 'master') {
    properties([[$class  : 'BuildDiscarderProperty',
                 strategy: [$class               : 'LogRotator',
                            artifactDaysToKeepStr: '',
                            artifactNumToKeepStr : '10',
                            daysToKeepStr        : '',
                            numToKeepStr         : '10']
                ]
    ])
} else {
    properties([[$class  : 'BuildDiscarderProperty',
                 strategy: [$class               : 'LogRotator',
                            artifactDaysToKeepStr: '',
                            artifactNumToKeepStr : '2',
                            daysToKeepStr        : '',
                            numToKeepStr         : '2']
                ]])
}

pipeline {
    agent any

    options {
        skipDefaultCheckout true
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Preparation') {
            steps('Create DB') {
                sh 'mysql -uroot < databases/tracker/create_databases.sql'
            }
            steps('Flyway migration') {
                sh 'echo "Migrate"'
            }
        }
        stage('Test') {
            steps('Run tests') {
                sh './gradlew clean build'
            }
        }
    }
}
