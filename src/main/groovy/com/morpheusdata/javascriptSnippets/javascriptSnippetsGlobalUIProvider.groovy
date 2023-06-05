package com.morpheusdata.javascriptSnippets

import com.morpheusdata.core.AbstractGlobalUIComponentProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Account
import com.morpheusdata.model.Instance
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.ContentSecurityPolicy
import com.morpheusdata.model.User
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import java.text.DecimalFormat
import groovy.sql.GroovyRowResult
import groovy.transform.ToString
import groovy.sql.Sql
import java.sql.Connection
import groovy.util.logging.Slf4j
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

@Slf4j
class JavascriptSnippetsGlobalUIProvider extends AbstractGlobalUIComponentProvider {
	Plugin plugin
	MorpheusContext morpheus

    String code = "javascript-snippets"
	String name = "JavaScript Snippets"

	JavascriptSnippetsGlobalUIProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
	}

	@Override
	HTMLResponse renderTemplate(User user, Account account) {
		ViewModel<Object> model = new ViewModel<>()
        def viewData = [:]

		// get the snippets from the plugin settings
        def Object settings
        try {
            def pluginSettings = morpheus.getSettings(plugin)
            def settingsOutput = ""
            pluginSettings.subscribe({outData -> settingsOutput = outData},{error -> println error.printStackTrace()})
            JsonSlurper slurper = new JsonSlurper()
            settings = slurper.parseText(settingsOutput)
            } catch (Exception ex){
                log.error("could not parse plugin settings")
            }

        // we don't get the nonce injected
        def nonse = morpheus.getWebRequest().getNonceToken()
        viewData["nonce"] = nonse.toString()

        viewData["version"] = plugin.version

        // snippet enable/disable
        // trim instance
        def enableTrim = "false"
        if (settings.trimInstanceName == "on") {
            enableTrim = "true"
        }

        //log.info(settings.trimInstanceName)
        //log.info(enableTrim)
        viewData["enableTrimInstanceName"] = enableTrim

        model.object = viewData
        getRenderer().renderTemplate("hbs/javascriptSnippets", model)
	}

	@Override
	Boolean show(User user, Account account) {
		def show = true

		plugin.permissions.each { Permission permission ->
		    if(user.permissions[permission.code] != permission.availableAccessTypes.last().toString()){
		 		 show = false
		 	}
		}

		return show
	}

	@Override
	ContentSecurityPolicy getContentSecurityPolicy() {
		def csp = new ContentSecurityPolicy()
		return csp
	}
}