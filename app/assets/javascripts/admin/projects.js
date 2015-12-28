app.controller('ProjectController', function($scope, $http) {
    $scope.project = {};
    $scope.saveProject = function() {
        $http.post(Routes.saveProject, JSON.stringify($scope.project)).success(function(data, status) {
            alert(data);
        }).error(function(data, status) {
			alert("WOLOWLO_EROR");
        });
    };

    $(function() {
		$("[data-add-project]").click(function() {
			$scope.project = {
				name: ""
			};
			$scope.$apply();
		});


		$("tr[data-id]").click(function() {
			$scope.project = {
				name: $(this).find("[data-name]").html(),
			};
			
			$scope.$apply();
			$("#add-project-modal").modal("show");
		});
	});
});
