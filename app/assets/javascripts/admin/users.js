app.controller('UserController', function($scope, $http) {
    $scope.user = {};
    $scope.saveUser = function() {
        $http.post(Routes.saveUser, JSON.stringify($scope.user)).success(function(data, status) {
            alert("WOLOWLO");
        }).error(function(data, status) {
			alert("WOLOWLO_EROR");
        });
    };

    $(function() {
		$("[data-add-user]").click(function() {
			$scope.user = {
				type: "admin",
				username: "",
				id: 0
			};
			$scope.$apply();
		});


		$("tr[data-id]").click(function() {
			$scope.user = {
				type: $(this).attr("data-type"),
				username: $(this).find("[data-username]").html(),
				id: $(this).attr("data-id")
			};
			
			$scope.$apply();
			$("#add-user-modal").modal("show");
		});
	});
});
