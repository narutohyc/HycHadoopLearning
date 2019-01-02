## python环境安装

1. AnaConda安装配置

   * 下载

     https://repo.anaconda.com/archive/

   * 安装文档

     http://docs.anaconda.com/anaconda/install/linux/

     ```shell
     bash ~/Downloads/Anaconda3-5.3.0-Linux-x86_64.sh
     ```

     可以自己输入安装路径

     重要！！！更新.bashrc文件，运行之后没有任何反应既为成功。

     ``` shell
     source ~/.bashrc
     ```

   * 安装ML常用包

     ``` shell
     conda install numpy scipy pandas scikit-learn matplotlib
     conda list 或import 包进行检查
     ```


2. Pycharm安装

   * 下载

     https://www.jetbrains.com/pycharm/download/#section=windows

   * 安装配置

     ``` shell
     tar -zxvf pycharm-professional-2018.3.2.tar.gz
     mv pycharm-2018.3.2 /opt/pycharm
     ```

     添加桌面图标

     ``` shell
     cd /usr/share/applications
     sudo gedit pycharm.desktop
     ```

     填入以下的内容

     ``` shell
     [Desktop Entry]
     Encoding=UTF-8
     Name=Pycharm
     Comment=Pycharm
     Exec=/opt/pycharm/bin/pycharm.sh
     Icon=/opt/pycharm/bin/pycharm.png
     Terminal=false
     StartupNotify=true
     Type=Application
     Categories=Application;Development;
     ```

   * 激活

     修改hosts文件，打开hosts文件，并在末尾添加如下内容

     ``` shell
     0.0.0.0 account.jetbrains.com
     ```

     保存退出，再从网上查找对应版本的激活码输入即可。


