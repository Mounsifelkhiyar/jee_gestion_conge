<%@ include file="parts/load.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="parts/includes.jsp"%>
<title>Gestion des cong�s</title>
<script type="text/javascript">
	$('#myModal').on('shown.bs.modal', function() {
		$('#myInput').trigger('focus')
	})
	$(document).ready( function() {
		for(i=1;i<=${nbrconge};i++)
		{
		$("#delete-site"+i).click( function() {
			var row = $(this).data('id');
			var ma=$(this).data('matricule');
			$(".deleteButton").attr("href","CongePersoController?action=delete&delBeginDate="+row+"&matricule=" + ma);
			
		});
		$("#view_info"+i).click(function() {
			var beginDate = $(this).data('begindate');
			var endDate = $(this).data('enddate');
			var duration = $(this).data('duration');
			var reason = $(this).data('reason');
			var type = $(this).data('type');
			var state = $(this).data('state');
			var validDate = $(this).data('validdate');
			var wording = $(this).data('wording');
					
			$('#viewBeginDate').text(beginDate); 
 			$('#viewEndDate').text(endDate);
			$('#viewDuration').text(duration);
			$('#viewReason').text(reason);
			$('#viewType').text(type);
			$('#viewState').text(state);
			$('#viewValidDate').text(validDate!=''?validDate:'Pas encore valid�');
			$('#viewWording').text(wording!=''?wording:'Pas de commentaire'); 
			
		});
		}
	});
</script>
</head>
<body>

	<%@ include file="parts/navbar.jsp"%>

	<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#SuppressionModal">Launch demo modal</button> -->

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
							<p>Voulez-vous validation la suppression du cong� : </p>
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


	<div class="modal fade" id="VisualisationConge" tabindex="-1"
		role="dialog" aria-labelledby="VisualisationCongeLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="VisualisationCongeLabel">Visualisation cong�</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col">
						 <ul class="list-group list-group-flush">
						  <li class="list-group-item"><b>Date de debut : </b><span id="viewBeginDate"></span></li>
						  <li class="list-group-item"><b>Date de fin : </b><span id="viewEndDate"></span></li>
						  <li class="list-group-item"><b>Dur�e du cong� : </b><span id="viewDuration"></span></li>
						  <li class="list-group-item"><b>Raison : </b><span id="viewReason"></span></li>
						  <li class="list-group-item"><b>Type du cong� : </b><span id="viewType"></span></li>
						  <li class="list-group-item"><b>Etat : </b><span id="viewState"></span></li>
						  <li class="list-group-item"><b>Date de validation : </b><span id="viewValidDate"></span></li>
						  <li class="list-group-item"><b>Commentaire : </b><span id="viewWording"></span></li>
						</ul> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<p> </p>
	<div class="container">

		<div class="row">
			<div class="col">
				<h1>Cong�s au cours de validation</h1>
			</div>
			<div class="col-2 align-self-center">
				<a href="CongeCreateController"><button class="btn btn-success">Nouveau cong�</button></a>
			</div>
		</div>
		<table class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th scope="col">matricule</th>
					<th scope="col">Date de d�but</th>
					<th scope="col">Date de fin</th>
					<th scope="col">Motif</th>
					<th scope="col">Type</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listConges}" var="item" varStatus="loop">
					<c:if test="${item.getValidDate() == null}">
						<tr>
							<th scope="row">${item.getmatricule()}</th>
							<td><c:out value="${item.getBeginDate()}" /></td>
							<td><c:out value="${item.getEndDate()}" /></td>
							<td><c:out value="${item.getReason()}" /></td>
							<td><c:out value="${item.getType()}" /></td>
							<td class="text-center">
								<a href="CongeEditController?matricule=${item.getmatricule()}&beginDate=${item.getBeginDate()}"><input class="btn btn-outline-dark" type="button" id="modifie-site" value="Modifier" /></a> 
								<input class="btn btn-outline-dark" data-id="${item.getBeginDate()}" data-toggle="modal" data-target="#SuppressionModal"
									type="button" id="delete-site${loop.index + 1}" data-matricule="${item.getmatricule()}" value="Supprimer" />
								<input class="btn btn-outline-dark btn-sm" data-toggle="modal" data-target="#VisualisationConge"
									data-beginDate="${item.getBeginDate()}" data-endDate="${item.getEndDate()}" 
									data-duration="${item.getDuration()}" data-reason="${item.getReason()}" 
									data-type="${item.getType()}" data-state="${item.getState()}" 
									data-validDate="${item.getValidDate()}" data-wording="${item.getWording()}"
									type="button" id="view_info${loop.index + 1}" value="..." />
									

							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		
	
	</div>
	<%@ include file="parts/footer.jsp"%>
</body>
</html>
