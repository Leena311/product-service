pipeline {
    agent any
    tools{
        maven 'maven_3_9_11'
    }
    stages {
            stage('Start MySQL') {
                steps {
                    script {
                        // Stop and remove any old container to avoid conflicts
                        bat 'docker rm -f mysql-test || exit 0'

                        // Start fresh MySQL container
                        bat '''
                            docker run -d --name mysql-test ^
                            -e MYSQL_ROOT_PASSWORD=root ^
                            -e MYSQL_DATABASE=product_service_db ^
                            -p 3307:3306 mysql:8
                        '''

                        // Wait for MySQL to be ready
                        bat 'timeout /t 30'
                    }
                }
            }

        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Leena311/product-service']])
                  bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t madhu973/product-service .'
                }
            }
        }
stage('Push image to Hub'){
            steps{
                script{
                 withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                     bat """
                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker push %DOCKER_USER%/product-service
                   """

                 }

                }
            }
        }
    }
}
