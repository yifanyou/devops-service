package io.choerodon.devops.app.service;

import java.util.List;

import io.choerodon.asgard.saga.feign.SagaClient;
import io.choerodon.devops.api.dto.*;
import io.choerodon.devops.domain.application.entity.DevopsEnvironmentE;
import io.choerodon.devops.domain.application.event.GitlabProjectPayload;

/**
 * Created by younger on 2018/4/9.
 */
public interface DevopsEnvironmentService {
    /**
     * 项目下创建环境
     *
     * @param projectId           项目Id
     * @param devopsEnviromentDTO 环境信息
     * @return String
     */
    String create(Long projectId, DevopsEnviromentDTO devopsEnviromentDTO);

    /**
     * 项目下环境流水线查询环境
     *
     * @param projectId 项目id
     * @param active    是否可用
     * @return List
     */
    List<DevopsEnvGroupEnvsDTO> listDevopsEnvGroupEnvs(Long projectId, Boolean active);


    /**
     * 项目下查询环境
     *
     * @param projectId 项目id
     * @param active    是否可用
     * @return List
     */
    List<DevopsEnviromentRepDTO> listByProjectIdAndActive(Long projectId, Boolean active);
    /**
     * 项目下查询环境
     *
     * @param projectId 项目id
     * @return List
     */
    List<DevopsEnviromentRepDTO> listDeployed(Long projectId);

    /**
     * 项目下启用停用环境
     *
     * @param environmentId 环境id
     * @param active        是否可用
     * @param projectId     项目id
     * @return Boolean
     */
    Boolean activeEnvironment(Long projectId, Long environmentId, Boolean active);

    /**
     * 项目下查询单个环境
     *
     * @param environmentId 环境id
     * @return DevopsEnvironmentUpdateDTO
     */
    DevopsEnvironmentUpdateDTO query(Long environmentId);

    /**
     * 项目下更新环境
     *
     * @param devopsEnvironmentUpdateDTO 环境信息
     * @param projectId                  项目Id
     * @return DevopsEnvironmentUpdateDTO
     */
    DevopsEnvironmentUpdateDTO update(DevopsEnvironmentUpdateDTO devopsEnvironmentUpdateDTO, Long projectId);

    /**
     * 项目下环境流水线排序
     *
     * @param environmentIds 环境列表
     * @return List
     */
    DevopsEnvGroupEnvsDTO sort(Long[] environmentIds);


    /**
     * 项目下查询单个环境的可执行shell
     *
     * @param environmentId 环境id
     * @param update        是否更新
     * @return String
     */
    String queryShell(Long environmentId, Boolean update);

    /**
     * 创建环境校验名称是否存在
     *
     * @param projectId 项目id
     * @param name      应用name
     */
    void checkName(Long projectId, String name);

    /**
     * 创建环境校验编码是否存在
     *
     * @param projectId 项目ID
     * @param code      应用code
     */
    void checkCode(Long projectId, String code);

    /**
     * 项目下查询有正在运行实例的环境
     *
     * @param projectId 项目id
     * @return List
     */
    List<DevopsEnviromentRepDTO> listByProjectId(Long projectId, Long appId);

    /**
     * 创建环境saga事件
     *
     * @param gitlabProjectPayload env saga payload
     */
    void handleCreateEnvSaga(GitlabProjectPayload gitlabProjectPayload);


    EnvSyncStatusDTO queryEnvSyncStatus(Long projectId, Long envId);

    String handDevopsEnvGitRepository(DevopsEnvironmentE devopsEnvironmentE);

    void initMockService(SagaClient sagaClient);
}
