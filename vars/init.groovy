def call(){
    //Agents
    env.JENKINS_MACHINE="jenkins-master"
    env.BUILD_MACHINE="build-machine"
    env.DEPLOY_MACHINE="deploy-machine"

    //Folder
    env.SOURCE_DIR="/home/build_user/src/spring-petclinic2"
    env.MR_SOURCE_DIR="/home/build_user/src/spring-petclinic2-mr"
    env.DB_BACKUP_DIR="/home/deploy_user/db_backup/"
    
    //URL
    env.NEXUS_URL="nexus:5000"
    env.MYSQL_URL="mysql-server"
    env.DEPLOY_URL="deploy-host"
    env.DEPLOY_PROD_PORT="8080"
    env.DEPLOY_ALPHA_PORT="8008"

    //Health check
    env.ATTEMPTS=30
    env.TIMEOUT=5


    //App info
    env.APP_NAME='spring-petclinic'
    env.APP_LOG_PATH='/var/log/spring-petclinic'

    // Trigger infor
    env.ORIGIN="git@gitlab.com:IrisStream/spring-petclinic2.git"
}