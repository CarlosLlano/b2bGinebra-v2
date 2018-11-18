node{
    //git repository configured outside this script
    stage 'checkout'
    checkout scm
    sh 'cd b2bGinebra/'

    //maven build
    stage 'build'
    sh 'mvn clean package' 

    //archive .war for build history
    stage 'archive' 
    archiveArtifacts 'target/*.war' 

    //deployment in docker
    stage 'deployment'
    sh 'cd ../deployment/'
    sh 'docker-compose stop' //stop existing containers
    sh 'docker-compose rm -f' //remove existing containers
    sh 'docker volume rm deployment_pg_data' //remove database volume, remove this part in production
    sh 'docker-compose up -d --build' //redeploy container
}