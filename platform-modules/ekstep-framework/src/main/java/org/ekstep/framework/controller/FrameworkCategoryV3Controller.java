package org.ekstep.framework.controller;

import java.util.Map;

import org.ekstep.common.dto.Request;
import org.ekstep.common.dto.Response;
import org.ekstep.common.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.ekstep.common.controller.BaseController;
import org.ekstep.framework.mgr.ICategoryInstanceManager;
import org.ekstep.telemetry.logger.PlatformLogger;

/**
 * This is the entry point for all CRUD operations related to categoryInstance for Framework API.
 * 
 * @author Rashmi
 *
 */
@Controller
@RequestMapping("/v3/framework/category")
public class FrameworkCategoryV3Controller extends BaseController {

	@Autowired
	private ICategoryInstanceManager categoryInstanceManager;
	
	/**
	 * 
	 * @param requestMap
	 * @param frameworkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> create(@RequestBody Map<String, Object> requestMap, 
			@RequestParam(value = "framework", required = true) String frameworkId) {
		String apiId = "ekstep.learning.categoryinstance.create";
		Request request = getRequest(requestMap);
		try {
			if(categoryInstanceManager.validateScopeId(frameworkId)) {
				Map<String, Object> map = (Map<String, Object>) request.get("category");
				Response response = categoryInstanceManager.createCategoryInstance(frameworkId, map);
				return getResponseEntity(response, apiId, null);
			} else {
				throw new ClientException("ERR_INVALID_FRAMEWORK_ID", "Invalid FrameworkId: " + frameworkId + " for Categoryinstance ", apiId, null);
		    }
		} catch (Exception e) {
			PlatformLogger.log("Create category instance", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
	
	/**
	 * 
	 * @param categoryInstanceId
	 * @param frameworkId
	 * @return
	 */
	@RequestMapping(value = "/read/{id:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> read(@PathVariable(value = "id") String categoryInstanceId,
			@RequestParam(value = "framework", required = true) String frameworkId) {
		String apiId = "ekstep.learning.categoryinstance.read";
		Response response;
		try {
			if(categoryInstanceManager.validateScopeId(frameworkId)) {
				response = categoryInstanceManager.readCategoryInstance(frameworkId, categoryInstanceId);
				return getResponseEntity(response, apiId, null);
			} else {
				throw new ClientException("ERR_INVALID_FRAMEWORK_ID", "Invalid FrameworkId: " + frameworkId + " for Categoryinstance ", apiId, null);
			}
		} catch (Exception e) {
			PlatformLogger.log("Read category instance", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
	
	/**
	 * 
	 * @param categoryInstanceId
	 * @param frameworkId
	 * @param requestMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update/{id:.+}", method = RequestMethod.PATCH)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable(value = "id") String categoryInstanceId,
			@RequestParam(value = "framework", required = true) String frameworkId,
			@RequestBody Map<String, Object> requestMap) {
		String apiId = "ekstep.learning.categoryinstance.update";
		Request request = getRequest(requestMap);
		try {
			if(categoryInstanceManager.validateScopeId(frameworkId)) {
				Map<String, Object> map = (Map<String, Object>) request.get("category");
				Response response = categoryInstanceManager.updateCategoryInstance(frameworkId, categoryInstanceId, map);
				return getResponseEntity(response, apiId, null);
			} else {
				throw new ClientException("ERR_INVALID_FRAMEWORK_ID", "Invalid FrameworkId: " + frameworkId + " for Categoryinstance ", apiId, null);
			}
		} catch (Exception e) {
			PlatformLogger.log("Update category", e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param frameworkId
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings({"unchecked","rawtypes"})
	public ResponseEntity<Response> search(@RequestBody Map<String, Object> map,
			@RequestParam(value = "framework", required = true) String frameworkId) {
		String apiId = "ekstep.learning.categoryinstance.search";
		Request request = getRequest(map);
		try {
			if(categoryInstanceManager.validateScopeId(frameworkId)) {
				Response response = categoryInstanceManager.searchCategoryInstance(frameworkId, (Map)request.get("search"));
				return getResponseEntity(response, apiId, null);
			} else {
				throw new ClientException("ERR_INVALID_FRAMEWORK_ID", "Invalid FrameworkId: " + frameworkId + " for Categoryinstance ", apiId, null);
			}
		} catch (Exception e) {
			PlatformLogger.log("search category instance | Exception: " , e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
	
	/**
	 * 
	 * @param categoryInstanceId
	 * @param frameworkId
	 * @return
	 */
	@RequestMapping(value = "/retire/{id:.+}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> retire(@PathVariable(value = "id") String categoryInstanceId,
			@RequestParam(value = "framework", required = true) String frameworkId) {
		String apiId = "ekstep.learning.categoryinstance.retire";
		try {
			if(categoryInstanceManager.validateScopeId(frameworkId)) {
				Response response = categoryInstanceManager.retireCategoryInstance(frameworkId, categoryInstanceId);
				return getResponseEntity(response, apiId, null);
			} else {
				throw new ClientException("ERR_INVALID_FRAMEWORK_ID", "Invalid FrameworkId:  " + frameworkId + " for Categoryinstance ", apiId, null);
			}
		} catch (Exception e) {
			PlatformLogger.log("retire category instance | Exception: " , e.getMessage(), e);
			return getExceptionResponseEntity(e, apiId, null);
		}
	}
}