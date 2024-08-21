pipeline {
    agent any

    environment {
        // 设置 Docker 镜像的标签
        FRONTEND_IMAGE = "luluplum/frontend:latest"
        BACKEND_IMAGE = "luluplum/backend:latest"
        DOCKER_REGISTRY = "your-docker-registry-url" // 如果你使用的是私有 Docker Registry，请修改此处
    }

    stages {
        stage('Build Frontend') {
            steps {
                script {
                    // 构建前端 Docker 镜像
                    sh 'docker build -t ${FRONTEND_IMAGE} ./frontend'
                }
            }
        }

        stage('Build Backend') {
            steps {
                script {
                    // 构建后端 Docker 镜像
                    sh 'docker build -t ${BACKEND_IMAGE} ./backend'
                }
            }
        }

        stage('Push Frontend Image') {
            steps {
                script {
                    // 推送前端 Docker 镜像到 Docker Registry
                    sh 'docker push ${FRONTEND_IMAGE}'
                }
            }
        }

        stage('Push Backend Image') {
            steps {
                script {
                    // 推送后端 Docker 镜像到 Docker Registry
                    sh 'docker push ${BACKEND_IMAGE}'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // 应用 Kubernetes 配置
                    sh 'kubectl apply -f k8s/frontend-deployment.yaml'
                    sh 'kubectl apply -f k8s/backend-deployment.yaml'
                }
            }
        }
    }

    post {
        always {
            // 这里可以添加一些清理步骤，例如清理工作目录或通知
            echo 'Cleaning up...'
        }
        success {
            echo 'Build and deployment succeeded!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }
}
