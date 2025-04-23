# 网络请求监听Chrome插件需求文档

## 1. 产品概述

"Network Listener"是一款Chrome浏览器扩展程序，专门用于监听和分析网页的网络请求。该插件能够捕获页面上发生的所有网络通信，记录请求和响应的详细信息，并支持将这些数据导出保存，方便开发人员和测试人员进行分析与调试。

## 2. 功能需求

### 2.1 核心功能

#### 2.1.1 请求监听
- 监听并记录页面上的所有网络请求(XHR、Fetch、WebSocket等)
- 捕获请求的URL、方法(GET、POST等)、请求头、请求参数和请求体
- 记录请求的发起时间、完成时间和持续时间
- 支持实时监听和展示请求信息

#### 2.1.2 响应捕获
- 捕获请求的响应状态码、响应头信息
- 记录并解析响应体内容(支持JSON、XML、HTML等常见格式)
- 对大型响应提供预览和完整查看选项
- 支持响应内容的格式化显示

#### 2.1.3 数据导出
- 导出数据按钮在列表数据的每一行，导出当前行请求的响应内容
- 支持将捕获的请求/响应数据导出为CSV格式文件
- 支持一键复制请求URL或响应内容到剪贴板
- 导出文件为解析响应内容，默认响应格式如下：
···
{"data": [{"id": "715578970506772480", "tenant_id": "1150971351520628737", "app_id": "202", "create_time": "2020-05-28 14:55:21.383000", "modify_time": "2020-05-28 14:55:21.383000", "create_user": "1197476284521824258", "modify_user": "1197476284521824258", "deleted": "False", "farm_id": "669205979719880704", "breeding_code": "DD", "stage": "NURSERY", "operator_name": "dsapp1", "target_pen_id": "669208346114097152", "target_location_name": "\u914d\u79cd\u820d-B9", "change_time": "2020-05-28 06:55:16.853000", "yzids": "\"1900-09ee3cbbc1e4b000\"", "operator_id": "1197476284521824258", "herd_code": "BY200528002", "source_pen_id": "669208236747620352", "source_location_name": "\u914d\u79cd\u820d-A9", "group_code": "None"}], "title": [{"title": "id", "resizable": "true", "key": "id", "fixed": "left", "width": 170}, {"title": "tenant_id", "resizable": "true", "key": "tenant_id", "width": 190}, {"title": "app_id", "resizable": "true", "key": "app_id", "width": 100}, {"title": "create_time", "resizable": "true", "key": "create_time", "width": 260}, {"title": "modify_time", "resizable": "true", "key": "modify_time", "width": 260}, {"title": "create_user", "resizable": "true", "key": "create_user", "width": 190}, {"title": "modify_user", "resizable": "true", "key": "modify_user", "width": 190}, {"title": "deleted", "resizable": "true", "key": "deleted", "width": 100}, {"title": "farm_id", "resizable": "true", "key": "farm_id", "width": 180}, {"title": "breeding_code", "resizable": "true", "key": "breeding_code", "width": 130}, {"title": "stage", "resizable": "true", "key": "stage", "width": 100}, {"title": "operator_name", "resizable": "true", "key": "operator_name", "width": 130}, {"title": "target_pen_id", "resizable": "true", "key": "target_pen_id", "width": 180}, {"title": "target_location_name", "resizable": "true", "key": "target_location_name", "width": 200}, {"title": "change_time", "resizable": "true", "key": "change_time", "width": 260}, {"title": "yzids", "resizable": "true", "key": "yzids", "width": 230}, {"title": "operator_id", "resizable": "true", "key": "operator_id", "width": 190}, {"title": "herd_code", "resizable": "true", "key": "herd_code", "width": 110}, {"title": "source_pen_id", "resizable": "true", "key": "source_pen_id", "width": 180}, {"title": "source_location_name", "resizable": "true", "key": "source_location_name", "width": 200}, {"title": "group_code", "resizable": "true", "key": "group_code", "width": 100}], "len": 1}
···
$.data 为数据内容，$.title[*].title 为标题


### 2.2 扩展功能

#### 2.2.1 过滤和搜索
- 按URL、域名、请求方法、状态码等条件过滤请求
- 提供搜索功能，快速定位特定请求
- 支持正则表达式搜索

#### 2.2.2 请求分析
- 统计请求的总数、成功率、平均响应时间等指标
- 可视化展示请求时间分布
- 标记异常请求(如404、500等错误)

## 3. 用户界面需求

- 简洁明了的请求列表，显示方法、URL、状态码和时间等关键信息
- 详细视图展示完整的请求和响应内容
- 提供过滤器和搜索栏，便于筛选和查找请求
- 支持暗色模式，减轻长时间使用的视觉疲劳
- 操作按钮布局合理，导出功能易于访问
- 界面采用侧边栏的展现方式
- 界面有三个按钮分别为，开始监听，停止监听，清除数据。

## 4. 技术实现要求

- 使用Chrome Extension Manifest V3规范开发
- 利用chrome.webRequest API或chrome.devtools.network API监听网络活动
- 前端界面使用HTML、CSS和JavaScript实现
- 确保插件对浏览器性能影响最小
- 数据存储考虑使用IndexedDB，支持大量请求记录的存储和查询

## 5. 安全与隐私

- 明确告知用户插件将访问网络请求数据
- 所有数据仅在用户本地处理和存储，不发送至任何远程服务器
- 提供选项让用户控制要监听的域名范围
- 支持过滤敏感信息(如Cookie、Authorization头等)的显示

## 6. 使用场景

1. 前端开发人员调试API请求和响应
2. QA测试人员验证接口返回数据
3. 网络分析师研究网站通信模式
4. 安全研究人员分析网站数据传输

## 7. 后续迭代计划

- 支持请求拦截和修改
- 添加请求比较功能，对比不同时间或不同环境下的请求差异
- 支持导入HAR文件进行分析
- 开发桌面版本，提供更强大的分析工具
