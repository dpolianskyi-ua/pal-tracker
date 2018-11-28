#!groovy
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
        stage('Initialize ENV') {
            steps {
                deleteDir()

                script {
                    env.PATH = "/home/jenkins/.gem/bin:${env.PATH}"
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
//                getCommitRevision()
//
//                script {
//                    currentBuild.displayName = "#${env.BUILD_NUMBER}-${env.GITHASH.substring(0, 7)}"
//                }

                stash 'sourceCode'
            }
        }

        stage('Assemble') {
            steps {
//                echo 'Reverting source code to latest branch ' + env.GITHASH + ' revision'
//                sh 'git reset --hard ' + env.GITHASH

                //Make all scripts executable
                sh 'chmod -R +x ./scripts'

                sh 'java -version'

                sh "./scripts/ci_assemble.sh"

                sh "./scripts/migrate-database.sh"

                stash 'assembledSourceCode'
            }
        }

        stage('Testing') {
            steps {
                // run goal for testing

                sh "./gradlew build"

                stash 'assembledSourceCodeForTesting'
            }
        }

        stage('Deployment') {
            steps {
//                deleteDir()

                script {
                    def envToDeploy = 'none'

                    if (env.BRANCH_NAME == 'master') {
                        envToDeploy = 'SET ENV TO DEPLOY'
                        stage('Deploying to SET ENV TO DEPLOY') {
                            deployApps(envToDeploy, 'pal-tracker')
//                            triggerContinuousDeliveryPipeline()
                        }
                    } else {

                    }
                }
            }
        }
    }
}

def deployApps(env, app) {
    def credentialsId = [
            'env-guid': 'SOME ENV GUID'
    ]
    wrap([$class                : 'CloudFoundryCliBuildWrapper',
          apiEndpoint           : 'SOME API ENDPOINT',
          cloudFoundryCliVersion: 'CloudFoundryCLI',
          credentialsId         : credentialsId[env],
          organization          : 'SOME ORG',
          space                 : 'SOME SPACE']) {

        sh 'cf push -f manifest.yml'
    }
}