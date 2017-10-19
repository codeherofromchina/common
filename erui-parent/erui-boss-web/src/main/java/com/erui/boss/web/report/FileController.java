package com.erui.boss.web.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/file")
public class FileController {
	private final static Logger logger = LoggerFactory.getLogger(FileController.class);

	/**
	 * 上传文件操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fileUpdate", method = RequestMethod.GET)
	public ModelAndView fileUpdate() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileUpdate");


		return mv;
	}

	/**
	 * 上传文件操作
	 * 
	 * @param file
	 *            文件
	 * @param otherInput
	 *            文件名称
	 * @return
	 */
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	@ResponseBody
	public Object updateFile(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam("common") String otherInput) {
		Map<String, Object> result = new HashMap<>();
		BufferedReader reader = null;
		try {
			boolean empty = file.isEmpty();
			if (!empty) {
				String originalFilename = file.getOriginalFilename();
				reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				result.put("code", 0);
				result.put("desc", "success");
				result.put("content", reader.readLine());
				result.put("originalFilename", originalFilename);
				result.put("otherInput", otherInput);
			} else {
				result.put("code", 1);
				result.put("desc", "文件为空");
			}
		} catch (Exception e) {
			logger.error("上传文件操作发生错误：{}", e.getMessage());
			result.put("code", 1);
			result.put("desc", "fail");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					reader = null;
				}
			}
		}

		return result;
	}
}
