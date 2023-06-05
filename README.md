## JavaScript Snippets Plugin

JavaScript snippets to do things you can't accomplish with CSS alone.

### Snippets

#### Trim Instance Name
Strips leading and trailing whitespace (in the standard Morpheus instance provisioning UI), which has been mis-keyed or copy and pasted in error .






### Build the plugin

Build the plugin locally with the included gradle wrapper.

```
./gradlew make
```

The compiled plugin will be available in the `./plugin` directory. 

Upload the plugin to Morpheus (admin > integrations > plugins tab)

Log out/in to Morpheus to ensure the new permission is applied.

Needs `whitelabel` enabled.

### Versions

The plugin has been configured and tested with Morpheus v.6.1.0

Plugin builds are tested/performed with Java v16
