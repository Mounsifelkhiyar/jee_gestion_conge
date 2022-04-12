<%@ include file="load.jsp"%>
<nav class="navbar navbar-expand-lg navbar navbar-dark bg-primary">
    <a href="CongePersoController" class="navbar-brand">Gestionnaire de congés</a>
    <div class="collapse navbar-collapse " >
        <a class="nav-link" style="color: #e3f2fd;"  href="CongePersoController">Gestion des conges</a>
	        <a class="nav-link " style="color: #e3f2fd;" href="CongeEmpController">Validation congés</a>
	        <a class="nav-link" style="color: #e3f2fd;" href="EmployeeController">Gestion des employés </a>
	        <a class="nav-link" style="color: #e3f2fd;" href="StatisticsController">Statistiques</a>
	        <a class="nav-link" style="color: #e3f2fd;" href="Initialiser_solde">Initialiser solde</a>

    </div>

    <div class="my-2 my-lg-0">
        <button class="btn btn-outline-success my-2 my-sm-0" onclick="location.href='AuthController?logout'" type="submit">Log out</button >
    </div>
</nav>
