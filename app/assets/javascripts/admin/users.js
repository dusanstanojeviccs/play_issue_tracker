app.controller('UserController', function($scope, $http) {
    $scope.user = {};
    $scope.error = false;
    $scope.errorMessage = ""; 

	String.prototype.capitalize = function() {
		return this.charAt(0).toUpperCase() + this.slice(1);
	};

    $scope.saveUser = function() {
        $http.post(Routes.saveUser, JSON.stringify($scope.user)).success(function(data, status) {
            if (!isNaN(Number(data)) && Number(data)!==0) {
				if ($scope.user.id===0) {
					$scope.user = {
						type: "admin",
						username: "",
						password: "",
						id:0
					};
					$("tbody").append(
					"<tr data-id=\""+data+"\" data-type=\""+$scope.user.type+"\">"+
					"<td data-type>"+$scope.user.type.capitalize()+"</td>"+
					"<td data-username>"+$scope.user.username+"</td>"+
					"<td data-password>"+$scope.user.password+"</td></tr>");
				} else {
					var $row = $("[data-id='"+$scope.user.id+"'][data-type='"+$scope.user.type+"']");
					$row.find("[data-type]").html($scope.user.type.capitalize());
					$row.find("[data-username]").html($scope.user.username);
					$row.find("[data-password]").html($scope.user.password);
				}

				$("#add-user-modal").modal("hide");
				$scope.error = false;
            } else {
				$scope.errorMessage = "Could not save"; 
				$scope.error = true;
            }
        }).error(function(data, status) {
			$scope.errorMessage = "Could not save"; 
			$scope.error = true;
        });
    };

    $(function() {
		$("body").on("click", "[data-add-user]", function() {
			$scope.error = false;
			$scope.user = {
				type: "admin",
				username: "",
				password: "",
				id:0
			};
			$scope.$apply();
		});


		$("body").on("click", "tr[data-id]", function() {
			$scope.error = false;
			$scope.user = {
				type: $(this).attr("data-type"),
				username: $(this).find("[data-username]").html(),
				password: $(this).find("[data-password]").html(),
				id: $(this).attr("data-id")
			};
			
			$scope.$apply();
			$("#add-user-modal").modal("show");
		});
	});
});
