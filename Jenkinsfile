pipeline {
    agent any
    stages {
        stage('Build Frontend') {
            steps {
                script {
                    dir('frontend') {
                        sh 'npm install --legacy-peer-deps'
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('Build Backend') {
            steps {
                script {
                    dir('backend') {
                        sh 'mvn clean package'
                    }
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker build -t frontend-image:latest ./frontend'
                    sh 'docker build -t backend-image:latest ./backend'
                    sh 'docker build -t db-image:latest ./db'
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh 'kubectl apply -f k8s/'
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    dir('frontend') {
                        sh 'npm test'
                    }
                    dir('backend') {
                        sh 'mvn test'
                    }
                }
            }
        }
    }
}