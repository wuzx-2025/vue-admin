## 快速目标 — 让 AI 编码代理立刻可用

以下说明针对本仓库的 monorepo 布局与本地工作流，优先提供可执行命令、关键文件位置与常见模式示例，便于自动化代理快速完成改动并生成可运行的变更。

### 代码库概览（大局）
- 这是一个 pnpm + turbo 管理的 monorepo（见根目录 `package.json`、`pnpm-workspace.yaml`）。
- 主要模块：
  - `apps/`：多个前端应用（`web-antd`, `web-ele`, `web-naive`, `web-tdesign`, `playground`）和后端示例（`backend-mock`、`backend-spring`）。
  - `packages/`：复用包（@vben/*），包含 composables、ui-kit、utils 等。
  - `internal/`, `scripts/`：内部构建/工具配置（例如 `vsh`, `turbo-run`）。

### 重要工作流命令（必会）
- 安装（全仓）：
  - `pnpm install`  （仓库强制使用 pnpm，见 `packageManager`）
- 启动开发环境（全局快捷）：
  - `pnpm dev` —— 由 `turbo-run dev` 代理到各子包的 dev 脚本。
- 单包运行/构建（常用）：
  - 在某个 app 内开发：`pnpm -F @vben/web-antd run dev`
  - 构建某个 app：`pnpm -F @vben/web-antd run build`
- 全量构建（CI / 本地大改动）：
  - `pnpm build` （会调用 `turbo build`，注意 Node 内存限制在 root scripts 中有设置）
- 检查、lint、类型：
  - `pnpm run check` （包含循环依赖、依赖检查、类型检查、拼写）
  - `pnpm lint` 或 `vsh lint`
  - `pnpm run check:type` 或 `pnpm -r run typecheck`
- 测试：
  - 单元：`pnpm test:unit`（vitest）
  - e2e：`pnpm run test:e2e`（由 turbo 跑各包的 e2e）

### 多语言注意和边界
- 前端与工具：TypeScript/Node/Vite（大部分代码在 `apps/*` 与 `packages/*`）。
- 后端示例：`apps/backend-spring` 是 Java（Maven/Gradle/IDE 处理），不要用 pnpm 脚本来管理 Java 构建。示例文件：`apps/backend-spring/ai/src/main/java/com/bl/ai/AiApplication.java`。
- `apps/backend-mock` 是 Node mock 服务，已集成到 vite 插件，通常不需单独启动。

### 项目约定 / 模式（对自动化很重要）
- 包名与导出：使用 `@vben/*` 约定（workspace packages），包间通过 workspace:* 互相依赖。
- 运行单包命令时优先使用 `pnpm -F <pkg>` 或 `pnpm -w -F`，避免全仓不必要的操作。
- 根 `package.json` 中定义了常用复合脚本（`build:antd`, `dev:ele` 等），自动化代理可调用这些脚本来定位具体包。
- 私有模块导入别名：部分包在 package.json 或 tsconfig 中使用以 #/ 开头的快捷导入（通常指向 src 目录），修改时注意保持 alias 同步（检查 imports 字段与 tsconfig/）。
- 项目为前后端分离的项目，apps/backend-spring 为后端的springboot应用
- springboot应用要符合springboot的规范，包括项目结构需要清晰，包括controller, service, repository,entity等包的划分。

### 变更依赖与新增包的流程（自动化代理要执行）
1. 新增包：在 `packages/` 下创建模块 -> 在 `pnpm-workspace.yaml` 的 `packages` 区域确保 glob 覆盖到新路径（通常已通配）。
2. 修改 package.json 依赖（workspace:*）：更新后执行 `pnpm install`。
3. 如更改共享类型或 API：运行 `pnpm -r run typecheck` 与 `pnpm test:unit`（视影响范围选择 -F 指定包）。

### 代码风格、提交与 CI 约定
- 提交使用约定式提交（conventional commits），仓库自带 `czg`/`cz-git`，可用 `pnpm run commit`。
- pre-commit/CI：lefthook 与 GitHub Actions 被使用（见根 README badges），自动化改动应确保 lint/type/test 在本地通过。

### 对代理的具体指导（短、可执行）
- 查找服务入口：前端 app 一般在 `apps/<app>/index.html` 与 `apps/<app>/src`。共享代码在 `packages/*/src`。
- 编辑 UI 组件：修改 `packages/@core/ui-kit` 或对应 `@vben/<pkg>`，通过 `pnpm -F <package> run dev` 在本地验证。
- 调整构建设置：修改 `internal/vite-config` 或 `vite-config/*`，再运行对应 app 的 `pnpm -F <app> run build`。
- 小变更验证顺序建议：`pnpm -F <pkg> run typecheck` -> `pnpm -F <pkg> run build` (或 `dev`) -> `pnpm test:unit`。

### 推荐检查点（提交 PR 前由代理自动跑）
- `pnpm -r run typecheck` 或 `pnpm run check:type`
- `pnpm lint` 或针对包 `pnpm -F <pkg> run lint`（若包有 lint 脚本）
- `pnpm test:unit`（快速），或 `pnpm -F <pkg> test`。

如果这份文件有遗漏（例如某个特定包的自定义脚本或特殊 CI 步骤），请告诉我要补充的目标包或场景，我会把示例和更精确的命令加入。 
