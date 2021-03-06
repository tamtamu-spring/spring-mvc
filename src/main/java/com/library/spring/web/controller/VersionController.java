package com.library.spring.web.controller;


import com.library.spring.web.model.VersionBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@RestController
public class VersionController {

	@GetMapping("/version")
	@ApiOperation(value = "Get Version", notes = "Retrieves the current version details of spring mvc application.", response = VersionBean.class)
	public VersionBean getVersion(HttpServletRequest request) throws IOException {
		return getVersion(request.getServletContext());
	}

	private VersionBean getVersion(ServletContext servletContext) throws IOException {

		VersionBean versionBean = new VersionBean();
		InputStream in = null;

		try {
			in = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");

			if(in != null) {
				Manifest manifest = new Manifest(in);
				Attributes attributes = manifest.getMainAttributes();
				versionBean.setArtifactId(attributes.getValue("artifactId"));
				versionBean.setVersion(attributes.getValue("version"));
				versionBean.setEnvironment("prod");
				versionBean.setBuildTimestamp(attributes.getValue("Build-Timestamp"));
				versionBean.setManifestVersion(attributes.getValue("Manifest-Version"));
				versionBean.setScmVersion(attributes.getValue("SCM-Version"));
			}

		} finally {
			if (in != null) {
				in.close();
			}
		}

		return versionBean;
	}
}