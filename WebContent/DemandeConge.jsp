
<%@ include file="parts/load.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="parts/includes.jsp"%>
<title>Nouvelle demande de congé</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	
	$('#myModal').on('shown.bs.modal', function() {
		$('#myInput').trigger('focus')
		})
	$(document).ready( function() {
	$( function() {
		$( "#datepicker" ).datepicker();
	} );
	
	$('#selectemp').change(function() {
		var nbconges1 = $('#selectemp option:selected').data('nbconges');
		$('#viewSolde').text(nbconges1); 

	});
	
	$('#duree').keyup(function() {
		var date_debut=$('#bday').val();
		var duree=parseInt($(this).val());
 		 var [yyyy, mm, dd] = date_debut.split("-");
		var d=new Date(yyyy,mm,dd);
		d.setDate(d.getDate()+duree);
		var dd = d.getDate();
	    var mm = d.getMonth();
	    var y = d.getFullYear();
	    var datf = y + '-' + mm + '-' + dd;
				$('#eday').val(datf);
	});
	
	
	});
	</script>

</head>
<body>
	
	<%@ include file="parts/navbar.jsp"%>

	<div class="container">
	
		<span>${errors['remainingBalance']}</span>
		<p> </p>
		<h1>Nouvelle demande de congé :</h1>
		
		<div class="row">
		
			<div class="col-3">
			</div>
			
			<div class="col-6">

				<form class="form-signin" action="CongeCreateController" method="POST">
				
				
				<div class="form-group">
						<label >Employe:</label> 
						<c:if test="${ !listEmployees.isEmpty()}">
						
						<select name="selectemp" id="selectemp"  class="form-control">
												<option selected disabled>Choisir un employe</option>
												
										
													<c:forEach items="${listEmployees}" var="item" varStatus="loop">
												
											
<option  data-toggle="modal" data-target="#viewSolde" data-nbconges="${item.getnbConges()}"  value="${item.getmatricule()}"><c:out value="${item.getmatricule()}"/>  <c:out value="${item.getFname()}"/>  <c:out value="${item.getName()}" /> </option>
							
				                 						
				                 		 			</c:forEach>
				                 				 </select>
						
						
									<!-- <table class="table table-bordered">
											<tr>	
												<td>		
												<select name="Employe"  class="form-control">
												<option selected disabled>Choisir un employe</option>
										
										
										
											
													<c:forEach items="${listEmployees}" var="item" varStatus="loop">
											
				                 						<option value="${item.getmatricule()}">${item.getmatricule()} </option>
				                 						
				                 		 			</c:forEach>
				                 				 </select>
				                 				 </td>
				                 		 	</tr> 
				                 		</table>-->
				                 		
				      </c:if>
				      
				     </div>	
				     
				 

					<div  class="form-group">
					<label>Solde employe :</label>
								
								 <span id="viewSolde"></span><br />
	
					</div>

  					<div class="form-row">
   						 <div class="col-7">
      						<label>Date de debut de congé :</label> <input type="date" name="bday" id="bday"
							max="3000-12-31" min="1000-01-01" class="form-control">
    					</div>
    				<div class="col">
    					<label>Duree congé :</label> 
      					<input type="number" class="form-control" name="duree" id="duree" placeholder="Saisir nombre conge">
    					</div>
  						</div>
		
								
					
					<div class="form-group">
						<label>Date de fin de congé :</label> <input type="date" name="eday" id="eday"
							min="1000-01-01" max="3000-12-31" class="form-control">
					</div>
		
					
				                 
					<div class="form-group">
						<label>Motif :</label> <select name="motif" class="form-control">
							<option selected disabled>Choisir un motif</option>
							<option value="Maladie">Maladie</option>
							<option value="Enfants malades">Enfants malades</option>
							<option value="Raisons personnelles">Raisons personnelles</option>
						</select>
					</div>
					
				
		
					<div class="form-group">
						<label>Type :</label> <select name="type" class="form-control">
							<option selected disabled>Choisir un type</option>
							<option value="Administartif">congé Administartif</option>
							<option value="Etranger">Congés à l'Etranger</option>
							<option value="Maternite">Maternite</option>
							<option value="Maladie">congé maladie</option>
							<option value="sans_solde">congé sans solde</option>
						</select>
					</div>
						

					<button class="btn btn-outline-success my-2 my-sm-0" name="action" value="create" type="submit">Valider</button>
						
				</form>
						
				<a href="CongePersoController"><button class="btn btn-outline-danger my-2 my-sm-0">Annuler</button></a>
				<p> </p>
				<p> </p>
						
			</div>
		</div>
	</div>

	<%@ include file="parts/footer.jsp"%>
</body>
</html>