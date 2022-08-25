pipeline { 
        agent none
        stages {
                stage('Maven build') {
                        agent any
                        steps {
                                sh 'mvn clean package -f ./ssafyproject1'
                        }
                }
                stage('Docker build') {
                        agent any
                        steps {
                                sh 'docker build -t backimg ./ssafyproject1'
                        }
                }
                stage('Docker run') {
                        agent any
                        steps {
                               

                                sh 'docker ps -f name=backimg -q \
                                        | xargs --no-run-if-empty docker container stop'

                               

                                sh 'docker container ls -a -f name=backimg -q \
                                        | xargs -r docker container rm'


                               
                                sh 'docker run -d --name backimg -p 8000:8000 backimg'
                        }     
                }
        } 

}
