pipeline {
    agent any
    tools {
        maven 'maven_3_9_11'
    }
    stages {

        stage('Start MySQL') {
            steps {
                script {
                    // Remove old container if exists
                    bat 'docker ps -a -q --filter "name=mysql-test" | findstr . && docker rm -f mysql-test || echo "No old container found"'

                    // Start fresh MySQL container
                    bat '''
                        docker run -d --name mysql-test ^
                        -e MYSQL_ROOT_PASSWORD=root ^
                        -e MYSQL_DATABASE=product_service_db ^
                        -p 3306:3306 mysql:8
                    '''

                    // Wait for MySQL to initialize
                    bat 'powershell -Command "Start-Sleep -Seconds 20"'

                    // Health check: Ensure MySQL is running
                    def status = bat(returnStatus: true, script: 'docker exec mysql-test mysqladmin -uroot -proot ping')
                    if (status != 0) {
                        error "❌ MySQL failed to start or is not reachable"
                    } else {
                        echo "✅ MySQL is up and running"
                    }
                }
            }
        }

        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Leena311/product-service']])
                bat 'mvn clean install'
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    bat 'docker build -t madhu973/product-service .'
                }
            }
        }

        stage('Push image to Hub') {
            steps {
                script {
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
