package io.choerodon.devops.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.app.service.ApplicationService;
import io.choerodon.devops.app.service.ApplicationServiceEx;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.devops.api.dto.ApplicationRepDTO;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/organizations/{id}/apps")
public class OrganizationApplicationController {

    @Autowired
    private ApplicationServiceEx applicationService;

    /**
     * 组织下分页查询应用
     *
     * @param orgId       组织id
     * @param isActive    项目是否启用
     * @param pageRequest 分页参数
     * @param params      参数
     * @return Page
     */
    @Permission(level = ResourceLevel.ORGANIZATION,
            roles = {"role/organization/default/deploy-administrator"})
    @ApiOperation(value = "组织下分页查询应用")
    @CustomPageRequest
    @PostMapping("/list_by_options")
    public ResponseEntity<Page<ApplicationRepDTO>> pageByOptions(
            @ApiParam(value = "项目Id", required = true)
            @PathVariable(value = "id") Long orgId,
            @ApiParam(value = "应用是否启用", required = false)
            @RequestParam(value = "active", required = false) Boolean isActive,
            @ApiParam(value = "应用是否存在版本", required = false)
            @RequestParam(value = "has_version", required = false) Boolean hasVersion,
            @ApiParam(value = "分页参数")
            @ApiIgnore PageRequest pageRequest,
            @ApiParam(value = "查询参数")
            @RequestBody(required = false) String params) {
        return Optional.ofNullable(
                applicationService.listByOptionsInOrg(orgId, isActive, hasVersion, pageRequest, params))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.appTemplate.get"));
    }
}
