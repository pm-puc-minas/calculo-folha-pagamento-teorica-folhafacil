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
				path: "admin",
				children: [
					{
						path: "funcionarios",
						component: FuncionariosPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FUNCIONARIOS_LISTAR'}
					},
					{
						path: "folha-salarial",
						component: FolhaPagamentoPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_FOLHA_SALARIAL_LISTAR'}
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
				]
			}
		]
	},
];
