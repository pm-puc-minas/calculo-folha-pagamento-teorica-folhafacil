import { Routes } from '@angular/router';
import { MainPage } from './pages/main-page/main-page';
import { FuncionariosPage } from './pages/admin/funcionarios-page/funcionarios-page';
import { FolhaSalarialPage } from './pages/admin/folha-salarial-page/folha-salarial-page';
import { LoginPage } from './pages/login-page/login-page';
import { RoleCanMatch, AuthCanMatch } from './auth/auth.guard';
import { HoraExtraPage } from './pages/funcionario/hora-extra-page/hora-extra-page';
import { NoPermissionPage } from './pages/no-permission-page/no-permission-page';
import { LogsPage } from './pages/admin/logs-page/logs-page';
import { BeneficiosPage } from './pages/admin/beneficios-page/beneficios-page';

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
						component: FolhaSalarialPage,
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
						component: HoraExtraPage,
						canActivate: [RoleCanMatch],
						data: { role : 'FF_HORA_EXTRAS_LISTAR'}
					},
				]
			}
		]
	},
];
