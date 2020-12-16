## Git命令

```shell
# 设置git库为大小写敏感
# 但是有bug，如果jvm改成JVM，那么提交后远程会同时存在jvm和JVM
git config core.ignorecase false

# 这时就要删除远程分支的无用的文件夹
# 加上 -n 这个参数，执行命令时，是不会删除任何文件，而是展示此命令要删除的文件列表预览。
git rm -r -n --cached 文件/文件夹名称 

# 删除文件
git rm -r --cached 文件/文件夹名称

# 然后提交即可·
git commit -m '说明'
git push (origin 分支名)
```

```git
# 查看该分支的所有标签
git tag
# 查看某个标签
git show 标签名
# 对某个版本号创建标签
git tab 标签名 版本号
# 删除标签：
git tag -d [标签名]

git tag [标签名] -m '[备注信息]' [版本号]

# 将标签push到远端
git push origin 标签名

# 推送全部本地仓库标签至远程仓库
git push origin --tags

```

### Github版本release

```git


```

