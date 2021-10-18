#!groovy

// requires Pipeline Utility Steps plugin
// requires Build Name and Description Setter plugin

properties([disableConcurrentBuilds()])

pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
        timestamps()
    }

    environment {
        CURRENT_VERSION = ''
    }

    stages {
        stage("read version") {
            steps {
                echo "=========== read version ============================================================"
                script {
//                    String versionString = readFile 'version.conf'
//                    if (!(versionString ==~ /version=\d+\.\d+\.\d+/))
//                        throw new Exception('wrong version string in file: ' + versionString)
//                    def versions = ((versionString.split('='))[1]).split('\\.')

                    def props = readProperties file: 'version.conf'
                    println props.version
                    CURRENT_VERSION = "${version}.$BUILD_NUMBER"
                    buildName "Version: ${CURRENT_VERSION}"
                }
            }
        }

//        stage("building apps") {
//            steps {
//                echo "=========== building apps ============================================================"
//                sh './gradlew clean'
//                sh './gradlew :kube:frontend:installFront :kube:frontend:buildFront'
//                sh './gradlew :kube:backend:build'
//                sh './gradlew :kube:gateway:build'
//            }
//        }
//
//        stage("build images") {
//            steps {
//                echo "=========== build frontend image ==========================================================="
//                sh "docker build -t dgcd/kubelab-frontend:$CURRENT_VERSION -f ./kube/frontend/Dockerfile ."
//
//                echo "=========== build gateway image ==========================================================="
//                sh "docker build -t dgcd/kubelab-gateway:$CURRENT_VERSION -f ./kube/gateway/Dockerfile ."
//
//                echo "=========== build backend image ==========================================================="
//                sh "docker build -t dgcd/kubelab-backend:$CURRENT_VERSION -f ./kube/backend/Dockerfile ."
//
//                echo "=========== build nodesrv image ==========================================================="
//                sh "sed -i 's/IMAGE_VERSION/${CURRENT_VERSION}/' ./kube/nodesrv/server.js"
//                sh "docker build -t dgcd/kubelab-nodesrv:$CURRENT_VERSION -f ./kube/nodesrv/Dockerfile ./kube/nodesrv/"
//            }
//        }
//
//        stage("docker login and push") {
//            steps {
//                echo "=========== docker repository login ========================================================="
//                withCredentials([usernamePassword(
//                        credentialsId: 'dockerhub_dgcd',
//                        usernameVariable: 'USERNAME',
//                        passwordVariable: 'PASSWORD'
//                )]) {
//                    sh "docker login -u $USERNAME -p $PASSWORD"
//                }
//                echo "=========== pushing docker images ============================================================"
//                sh "docker push dgcd/kubelab-frontend:$CURRENT_VERSION"
//                sh "docker push dgcd/kubelab-gateway:$CURRENT_VERSION"
//                sh "docker push dgcd/kubelab-backend:$CURRENT_VERSION"
//                sh "docker push dgcd/kubelab-nodesrv:$CURRENT_VERSION"
//                echo "=========== docker repository logout ========================================================="
//                sh "docker logout"
//            }
//        }
//
//        stage("deploy to prod server") {
//            steps {
//                echo "=========== deploy to prod server ============================================================"
//                sh """
//                    ssh hedg@lab bash -c "'
//                        cd ~/containers/kubelab/prod
//                        rm -rf ./tmp*
//                        mkdir ./tmp-${CURRENT_VERSION}
//                        cd ./tmp-${CURRENT_VERSION}
//                        curl -sS https://raw.githubusercontent.com/dgcd/java-studies/master/kube/config/deploy/resources/backend.yaml  --output backend.yaml
//                        curl -sS https://raw.githubusercontent.com/dgcd/java-studies/master/kube/config/deploy/resources/gateway.yaml  --output gateway.yaml
//                        curl -sS https://raw.githubusercontent.com/dgcd/java-studies/master/kube/config/deploy/resources/frontend.yaml --output frontend.yaml
//                        curl -sS https://raw.githubusercontent.com/dgcd/java-studies/master/kube/config/deploy/resources/nodesrv.yaml  --output nodesrv.yaml
//                        curl -sS https://raw.githubusercontent.com/dgcd/java-studies/master/kube/config/deploy/resources/istio.yaml    --output istio.yaml
//                        sed -i 's/IMAGE_VERSION/${CURRENT_VERSION}/' backend.yaml
//                        sed -i 's/IMAGE_VERSION/${CURRENT_VERSION}/' gateway.yaml
//                        sed -i 's/IMAGE_VERSION/${CURRENT_VERSION}/' frontend.yaml
//                        sed -i 's/IMAGE_VERSION/${CURRENT_VERSION}/' nodesrv.yaml
//                        kubectl apply -f backend.yaml --namespace=kubelab-ns
//                        kubectl apply -f gateway.yaml --namespace=kubelab-ns
//                        kubectl apply -f frontend.yaml --namespace=kubelab-ns
//                        kubectl apply -f nodesrv.yaml --namespace=kubelab-ns
//                        kubectl apply -f istio.yaml --namespace=kubelab-ns
//                    '"
//                """
//                echo "=========== Done! ==========================================================================="
//            }
//        }
    }
}
