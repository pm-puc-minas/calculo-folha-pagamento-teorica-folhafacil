0

Segui o tutorial disponível no README.md, mas na hora de compilar o backend end com mvn clean install -DskipTests, obtive o erro a seguir:

```[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR :
[INFO] -------------------------------------------------------------
[ERROR] /home/paulohdscoelho/projetos/aulas_PUC/trabalho_final/calculo-folha-pagamento-teorica-folhafacil/backend/folhafacil/src/test/java/com/folhafacil/folhafacil/ServiceTeste/BeneficoServiceImplUnitTest.java:[65,19] constructor FolhaPagamentoServiceImpl in class com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl cannot be applied to given types;
  required: com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl
  found:    com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository
  reason: actual and formal argument lists differ in length
[ERROR] /home/paulohdscoelho/projetos/aulas_PUC/trabalho_final/calculo-folha-pagamento-teorica-folhafacil/backend/folhafacil/src/test/java/com/folhafacil/folhafacil/ServiceTeste/FolhaPagamentoServiceImplTeste.java:[65,19] constructor FolhaPagamentoServiceImpl in class com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl cannot be applied to given types;
  required: com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl
  found:    com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository
  reason: actual and formal argument lists differ in length
[INFO] 2 errors
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.626 s
[INFO] Finished at: 2025-12-13T11:00:06-03:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.0:testCompile (default-testCompile) on project folhafacil: Compilation failure: Compilation failure:
[ERROR] /home/paulohdscoelho/projetos/aulas_PUC/trabalho_final/calculo-folha-pagamento-teorica-folhafacil/backend/folhafacil/src/test/java/com/folhafacil/folhafacil/ServiceTeste/BeneficoServiceImplUnitTest.java:[65,19] constructor FolhaPagamentoServiceImpl in class com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl cannot be applied to given types;
[ERROR]   required: com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl
[ERROR]   found:    com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository
[ERROR]   reason: actual and formal argument lists differ in length
[ERROR] /home/paulohdscoelho/projetos/aulas_PUC/trabalho_final/calculo-folha-pagamento-teorica-folhafacil/backend/folhafacil/src/test/java/com/folhafacil/folhafacil/ServiceTeste/FolhaPagamentoServiceImplTeste.java:[65,19] constructor FolhaPagamentoServiceImpl in class com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl cannot be applied to given types;
[ERROR]   required: com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl
[ERROR]   found:    com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl,com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl,com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.service.KeycloakService,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository,com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository,com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl,com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository
[ERROR]   reason: actual and formal argument lists differ in length
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```