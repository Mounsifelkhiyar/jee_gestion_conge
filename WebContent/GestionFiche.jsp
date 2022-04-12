<%@ include file="parts/load.jsp"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="parts/includes.jsp"%>
<title>Gestion des fiches employés</title>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
<script>
	$(document).ready( function() {
		//alert("**"+${nbemp});
		$('#table_id').DataTable();
		for(i=1;i<=${nbemp};i++)
			{
		$("#delete-site"+i).click( function() {
			var row = $(this).data('id');
			$(".deleteButton").attr("href","EmployeeController?action=delete&delmatricule=" + row);
			
		});
		
			
		$("#view-fiche"+i).click(function() {
			
			var matricule = $(this).data('matricule');
			var fname = $(this).data('fname');
			var name = $(this).data('name');
			var address = $(this).data('address');
			var city = $(this).data('city');
			var zipcode = $(this).data('zipcode');
			var departements = $(this).data('departements');
			var role = $(this).data('role');
			var mail = $(this).data('mail');
			var nbconges = $(this).data('nbconges');
					//alert("matricule"+matricule+"text");
			console.log($(this).data('matricule'))
			
			
			$('#matricule').text(matricule); 
			$('#fname').text(fname);
			$('#name').text(name);
			$('#address').text(address);
			$('#zipcode').text(zipcode);
			$('#city').text(city);
			$('#departements').text(departements);
			$('#role').text(role);
			$('#mail').text(mail);
			$('#nbconges').text(nbconges);
			
		});
		}
	});
</script>
</head>
<body>

	<%@ include file="parts/navbar.jsp"%>
	
	<div class="modal fade" id="SuppressionModal" tabindex="-1" role="dialog"
		aria-labelledby="SuppressionModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="SuppressionModalLabel">Validation suppression</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
							<p>Voulez-vous validation la suppression de la fiche : </p>
						</div>
					</div>
					<div class="row">
						<div class="col text-right">

							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Annuler</button>
							<a class="deleteButton"><button type="button"
									class="btn btn-primary">Supprimer</button></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="viewFiche" tabindex="-1" role="dialog"
		aria-labelledby="viewFicheLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="viewFicheLabel">Visualisation Fiche :</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
							<ul class="list-group list-group-flush">
							 <li class="list-group-item"><b>matricule : </b><span id="matricule" ></span></li>
							 <li class="list-group-item"><b>Nom : </b><span id="fname"></span></li>
							 <li class="list-group-item"><b>Prénom : </b><span id="name"></span></li>
							 <li class="list-group-item"><b>Adresse : </b><span id="address"></span>, <span id="zipcode"></span>, <span id="city"></span> </li>
							 <li class="list-group-item"><b>Departements : </b><span id="departements"></span></li>
							 <li class="list-group-item"><b>Role : </b><span id="role"></span></li>
							 <li class="list-group-item"><b>Mail : </b><span id="mail"></span></li>
							 <li class="list-group-item"><b>Nombre de congés disponible : </b><span id="nbconges"></span></li>
							</ul> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
	
		<div class="row">
			<div class="col">
			<p> </p>
				<h1>Liste des employés :</h1>
			</div>
			<div class="col-2 align-self-center">
			<p> </p>
				<button class="btn btn-success" onclick="location.href='EmployeeCreateController'">Nouvel employé</button>
			</div>
		</div>
		<table id="table_id" class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th scope="col">matricule</th>
					<th scope="col">Prénom</th>
					<th scope="col">Nom</th>
					<th scope="col">Departements</th>
					<th scope="col">Role</th>
					<th scope="col">Mail</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${a}" var="item" varStatus="loop">
					<tr>
						<th scope="row">${item.getmatricule()}</th>
						<td><c:out value="${item.getFname()}" /></td>
						<td><c:out value="${item.getName()}" /></td>
						<td><c:out value="${item.getdepartements()}" /></td>
						<td><c:out value="${item.getRole()}" /></td>
						<td><c:out value="${item.getMail()}" /></td>
						<td class="text-center">
							<a href="EmployeeEditController?matricule=${item.getmatricule()}"><input class="btn btn-outline-dark" type="button" id="modifie-site" value="Modifier" /></a> 
							<input class="btn btn-outline-dark" data-id="${item.getmatricule()}" data-toggle="modal" data-target="#SuppressionModal"
									type="button" id="delete-site${loop.index + 1}" value="Supprimer" />
							<input class="btn btn-outline-dark btn-sm" data-toggle="modal" data-target="#viewFiche"
								data-matricule="${item.getmatricule()}" data-fname="${item.getFname()}" 
								data-name="${item.getName()}" data-address="${item.getAddress()}" 
								data-city="${item.getCity()}" data-zipcode="${item.getZipCode()}" 
								data-departements="${item.getdepartements()}" data-role="${item.getRole()}"
								data-mail="${item.getMail()}" data-nbconges="${item.getnbConges()}"
								type="button" id="view-fiche${loop.index + 1}" value="..." />
								
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<%@ include file="parts/footer.jsp"%>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.js"></script>
	
</body>
</html>