package script.db

databaseChangeLog(logicalFilePath: 'dba/devops_gitlab_personal_tokens.groovy') {
    changeSet(author: 'mumutu', id: '2018-11-26-create-table') {
        createTable(tableName: "devops_gitlab_personal_tokens", remarks: 'DevOps 用户token表') {
            column(name: 'id', type: 'BIGINT UNSIGNED', remarks: '主键，自增ID', autoIncrement: true) {
                constraints(primaryKey: true)
            }
            column(name: 'gitlab_user_id', type: 'BIGINT UNSIGNED', remarks: 'Gitlab用户ID')
            column(name: 'user_id', type: 'BIGINT UNSIGNED', remarks: 'Iam用户ID')
            column(name: "token", type: "VARCHAR(512)") {
                constraints(nullable: false)
            }
         }
    }
}