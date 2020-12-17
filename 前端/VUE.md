## VUE

```powershell
vue init webpack projectName


D:\Data\vue_project>vue init webpack my-project

? Project name my-project
? Project description 我的项目
? Author y
? Vue build standalone
? Install vue-router? Yes
? Use ESLint to lint your code? No
? Set up unit tests No
? Setup e2e tests with Nightwatch? No
? Should we run `npm install` for you after the project has been created? (recommended) npm
```

新手还是暂时不要用eslint吧，约束太严格了

然后去到项目文件夹，也就是vue_project下面的my-project文件夹

```vue
npm run dev   这是2.0的启动方法
npm run serve 这是3.0的启动方法
package.json中可以修改
```

```powershell
 DONE  Compiled successfully in 1886ms                                                                       上午9:31:09

 I  Your application is running here: http://localhost:8080
```

打开http://localhost:8080或者http://localhost:8080/#/ 即可



idea打开，下载vue.js插件，重启即可（idea默认生成3.0的vue）



### 插件

#### mavon-editor 

vue的md编辑器

##### 安装(当前项目目录下)：

```powershell
npm install mavon-editor --save
```

##### 基本使用：

main.js

```js
    // 全局注册
    import Vue from 'vue'
    import mavonEditor from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    // use
    Vue.use(mavonEditor)
    new Vue({
        'el': '#main'
    })
```

具体组件：

```html
<div id="main">
    <mavon-editor v-model="value"/>
</div>
```

图片上传：

```html
 <el-main class="content-content">
     <mavon-editor 
     v-model = 'editorContent'
     :ishljs="true"
      :codeStyle="code_style"
     ref=md @imgAdd="$imgAdd" @imgDel="$imgDel"
      />
 </el-main>
```

#### element-ui

```powershell
npm -i element-ui -S
```

使用(main.js)

```js
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
```

