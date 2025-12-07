import { Routes } from '@angular/router';
import { MainPage } from './pages/main-page/main-page';
import { FuncionariosPage } from './pages/admin/funcionarios-page/funcionarios-page';
import { LoginPage } from './pages/login-page/login-page';
import { RoleCanMatch, AuthCanMatch } from './auth/auth.guard';
import { NoPermissionPage } from './pages/no-permission-page/no-permission-page';
import { LogsPage } from './pages/admin/logs-page/logs-page';
import { BeneficiosPage } from './pages/admin/beneficios-page/beneficios-page';
import { MinhasHorasExtrasPage } from './pages/funcionario/minhas-horas-extras-page/minhas-horas-extras-page';
import { FolhaPagamentoPage } from './pages/admin/folha-pagamento-page/folha-pagamento-page';
import { MeusBeneficiosPage } from './pages/funcionario/meus-beneficios-page/meus-beneficios-page';
import { MeusPagamentosPage } from './pages/funcionario/meus-pagamentos-page/meus-pagamentos-page';
import { DashboardPage } from './pages/dashboard/dashboard';
import { HorasExtrasPage } from './pages/admin/horas-extras-page/horas-extras-page';

export const routes: Routes = [
	{ path: "", redirectTo: "login", pathMatch: "full" },
	{
		path: "login",
		component: LoginPage,
		canActivate: [AuthCanMatch]
	},
	{
		path: "no-permission",
		component: NoPermissionPage,
	},
	{ 
		path: "main", 
		component: MainPage,
		canActivate:[RoleCanMatch],
		children: [
			{
				path: "",
				component: DashboardPage,
				data: { public: true }
			},
			{
				path: "admin",
				children: [
					{
						path: "funcionarios",
						component: FuncionariosPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FUNCIONARIOS_LISTAR'}
					},
					{
						path: "folha-pagamento",
						component: FolhaPagamentoPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FOLHA_PAGAMENTO_LISTAR'}
					},
					{
						path: "logs",
						component: LogsPage,
						canActivate: [RoleCanMatch],
						data : { role : 'FF_LOGS_LISTAR'}
					},
					{
						path: "beneficios",
						component: BeneficiosPage,
						canActivate: [RoleCanMatch],
						data : { role : 'FF_BENEFICIO_LISTAR'}
					},
					{
						path: "horas-extras",
						component: HorasExtrasPage,
						canActivate: [RoleCanMatch],
						data: { role: 'FF_HORAS_EXTRAS_LISTAR'}
					}
				],
			},
			{
				path: "funcionario",
				children: [
					{
						path: "hora-extra",
						component: MinhasHorasExtrasPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_MINHA_HORA_EXTRA_LISTAR'}
					},
					{
						path: "meus-beneficios",
						component: MeusBeneficiosPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FUNCIONARIO_MEUS_BENEFICIOS_LISTAR'}
					},
					{
						path: "meus-pagamentos",
						component: MeusPagamentosPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FOLHA_PAGAMENTO_MEUS_PAGAMENTOS'}
					},
				]
			}
		]
	},
];
