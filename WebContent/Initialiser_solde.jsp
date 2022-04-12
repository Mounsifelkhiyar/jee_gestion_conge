
<%@ include file="parts/load.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="parts/includes.jsp"%>
<meta charset="UTF-8">
<title>Initialiser solde</title>
</head>
<body>

<%@ include file="parts/navbar.jsp"%>
<p> </p>
<p> </p>
<div class="alert alert-success" role="alert">
  <h4 class="alert-heading">ATTENTION!</h4>
  <p>Il faut oubligatoirement cliquer sur ce bouton qu'aprés une annnée.</p>
  <p>  </p>
</div>

<div class="container">
	
		<div class="row">
		
			<div class="col-3">
			</div>
			<div class="col-6">
		
						<a href="Initialiser_solde?action=in"><button class="btn btn-outline-danger my-2 my-sm-0" name="load" value="load" type="submit">Load reliquat</button></a>
				
				</div>
			</div>
</div>

<p> </p>
<p> </p>
				
<%@ include file="parts/footer.jsp"%>
</body>
</html>