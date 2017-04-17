package com.uws.webservice;

import com.uws.core.base.IBaseService;

/**
 * 
* @ClassName: IDromService 
* @Description: 宿舍信息同步
* @author 联合永道
* @date 2016-5-27 上午10:47:58 
*
 */
public interface IDromService extends IBaseService
{
	public String isTuiSu(String studentId);
}
