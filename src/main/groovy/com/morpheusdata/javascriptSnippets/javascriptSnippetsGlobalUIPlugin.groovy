package com.morpheusdata.javascriptSnippets

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HandlebarsRenderer
import com.morpheusdata.model.OptionType
import groovy.util.logging.Slf4j

@Slf4j
class JavascriptSnippetsGlobalUIPlugin extends Plugin {

    String code = "javascript-snippets"

	@Override
	void initialize() {
	    // set additional metadata
		this.setName("JavaScript Snippets")
		this.setDescription("Helper JavaScript snippets for Morpheus")
		this.setAuthor("Ollie Phillips")

        // call and register provider
        JavascriptSnippetsGlobalUIProvider javascriptSnippetsGlobalUIProvider = new JavascriptSnippetsGlobalUIProvider(this, morpheus)
        this.pluginProviders.put(javascriptSnippetsGlobalUIProvider.code, javascriptSnippetsGlobalUIProvider)

        // create a permission
		this.setPermissions([Permission.build('JavaScript Snippets Plugin','javascript-snippets', [Permission.AccessType.none, Permission.AccessType.full])])

	    // create option types for the enabling/disabling desired javascript snippets
        this.settings << new OptionType(
            name: "TrimInstanceName",
            code: "jss-trim-instance",
            fieldName: "trimInstanceName",
            displayOrder: 0,
            fieldLabel: "Trim Instance Name",
            helpText: 'Trim leading and trailing whitespace from instance name when provisioning',
            required: false,
            inputType: OptionType.InputType.CHECKBOX
        )
	}

	@Override
	void onDestroy() {}
}