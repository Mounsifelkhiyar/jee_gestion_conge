<%@ include file="parts/load.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="parts/includes.jsp"%>
<title>Gestion des congés</title>

	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.css">
	 
	 
<script type="text/javascript">

		
	$('#myModal').on('shown.bs.modal', function() {
	$('#myInput').trigger('focus')
	})
	$(document).ready( function() {
		
		$('#table_id').DataTable();
		
	
		
		/*$('#recherchemat').change(function() {
				var valeur=$(this).val();
				var valeur1=$(this).text();
				var indice=0;
				var indice1=0;
				alert("--"+valeur1);
			   
							for(var i=1;i<=${nbrcongevalide};i++)
			   					{
			   						var matricule_tr =$('#tabval tr[id='+i+']').data('matricule');
			   						if(valeur == matricule_tr)
										{
			   								indice++;
										}
			   							
								}
							
							if(indice==0)
								{
								alert("matricule n existe pas");
								}
							
							else
								{
									for(var j=1;j<=${nbrcongevalide};j++)
		   								{
		   									var matricule_tr =$('#tabval tr[id='+j+']').data('matricule');
		   									if(valeur != matricule_tr)
													{
		   												$('#tabval tr[id='+j+']').remove();
		   									
													}
		   									
										}
								}
	
		});*/
	});
</script>
</head>
<body>

	<%@ include file="parts/navbar.jsp"%>

	<div class="container">
		
		<span>${errors['wording']}</span>
		<p> </p>
		
		<div class="row">
			<div class="col">
				<h1>Congés des employés à valider</h1>
			</div>
		</div>
			
		
		<table class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th scope="col" class="text-center">matricule</th>
					<th scope="col" class="text-center">Congés</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${ listCongesToValid.isEmpty()}">
				<tr>
					<td colspan="2" class="text-center">Aucun congés pris</td>
				</tr>
			</c:if>
			
			<c:if test="${!listCongesToValid.isEmpty()}">
				<c:forEach items="${listCongesToValid}" var="item" varStatus="loop">
				
				
				
					<tr>
						<th scope="row" class="text-center">${item.getmatricule()}</th>
						<td>
								<form action="CongeEmpController" method="GET">
									<p>Nom et Prénom : ${item.getNameEmploye()}</p>
									<p>Du  : ${item.getBeginDate()} Au : ${item.getEndDate()}</p>
									<p>duree : ${item.getDuration()}</p>
									<p>Motif : ${item.getReason()}</p>
									<p>Type : ${item.getType()}</p>
									<input type="hidden" name="upmatricule" value=${item.getmatricule()}></input>
									<input type="hidden" name="upBday" value=${item.getBeginDate()}></input>
									
									<div class="form-group w-50">
										<label for="upWording">Laisser un commentaire :</label>
										<textarea class="form-control" id="upWording" name="upWording" rows="3" cols="0"></textarea>
										<!-- <input type="text" class="form-control" id="upWording" placeholder="Commentaire"> -->
									</div>
  
  
									<!-- <label for="upWording">Laisser un commentaire :</label>
									<textarea name="upWording" rows="" cols=""></textarea>
									 -->
									
									<button class="btn btn-outline-success my-2 my-sm-0" name="action"
										value="accept">Accepter</button>
									<button class="btn btn-outline-danger my-2 my-sm-0" name="action"
										value="decline">Refuser</button>
									<br />
								</form>
							</td>
						</tr>
					</c:forEach>
					<hr />
				</c:if>
			</tbody>
		</table>	
	
	
					<!-- <div class="form-group">
						<input  type="text" class="col-md-6 col-12 mt-10" name="recherchemat" id="recherchemat" data-target="#RechercheConge" placeholder="Recherche employe" required>
						<input  class="btn btn-outline-success my-2 my-sm-0" name="action" id="recherchemat1" data-matricule="${item.getmatricule()}"
										type="button" value="Recherche" />
					</div> 
								-->
		<div class="row">
			<div class="col">
				<h1>Congés validés</h1>
			</div>
		</div>
		 
	<a href="export.xls"><button class="btn btn-outline-danger my-1 my-sm-0" name="action" value="load" type="submit">exporter_excel</button></a> 
	
		<table id="table_id" class="table table-bordered">

			<thead class="thead-light">
				<tr>
					<th scope="col">matricule</th>
					<th scope="col">Nom et Prénom</th>
					<th scope="col">Date de début</th>
					<th scope="col">Date de fin</th>
					<th scope="col">Duree</th>
					<th scope="col">Motif</th>
					<th scope="col">Type</th>
					<th scope="col">Imprimer</th>
				</tr>
			</thead>
			<tbody id="tabval">
				<c:if test="${ listCongesValidated.isEmpty()}">
					<tr>
						<td colspan="7" class="text-center">Aucun congés pris</td>
					</tr>
				</c:if>
				<c:if test="${ !listCongesValidated.isEmpty()}">
					<c:forEach items="${listCongesValidated}" var="item" varStatus="loop">
					
					
						<tr id="${loop.index + 1}" name="idConge" class="mat" data-matricule="${item.getmatricule()}" ">
						
							<th scope="row">${item.getmatricule()}</th>
							<td ><c:out value="${item.getNameEmploye()}" /></td>
							<td><c:out value="${item.getBeginDate()}" /></td>
							<td><c:out value="${item.getEndDate()}" /></td>
							<td>Duree :<c:out value="${item.getDuration()}" /></td>
							<td><c:out value="${item.getReason()}" /></td>
							<td><c:out value="${item.getType()}" /></td>
							<td><a href="pdf_conge_annuel?matricule=${item.getmatricule()}&duration=${item.getDuration()}&beginDate=${item.getBeginDate()}&endDate=${item.getEndDate()}"><button class="btn btn-outline-danger my-2 my-sm-0" name="action" value="load" type="submit">conge_annuel</button></a>
								<a href="pdf_Decision_de_conge_annuel?matricule=${item.getmatricule()}&duration=${item.getDuration()}&beginDate=${item.getBeginDate()}&endDate=${item.getEndDate()}"><button class="btn btn-outline-danger my-1 my-sm-0" name="action" value="load" type="submit">Decision_de_ conge_annuel</button></a>
							</td>
							
						</tr>
					</c:forEach>
					<hr />
				</c:if>
			</tbody>

		</table>
		
		
	</div>	
	
	<%@ include file="parts/footer.jsp"%>
	
	
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.js"></script>
	 
</body>
</html>