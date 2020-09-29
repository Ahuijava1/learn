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

