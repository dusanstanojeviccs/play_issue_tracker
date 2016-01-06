app.controller('ProjectController', function($scope, $http) {
    $scope.project = {};
    $scope.error = false;
    $scope.errorMessage = "";

    $scope.saveProject = function() {
        $http.post(Routes.saveProject, JSON.stringify($scope.project)).success(function(data, status) {
            if (!isNaN(Number(data)) && Number(data)!==0) {
				if (isNaN(Number($scope.project.id)))
					$("tbody").append("<tr><td data-id>"+data+"</td><td data-name>"+$scope.project.name+"</td></tr>");
				else
					$("tbody").find("[data-id]").each(function(e) {
						if ($(this).html()===$scope.project.id)
							$(this).parent().find("[data-name]").html($scope.project.name);
					});
				$("#add-project-modal").modal("hide");
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
		$("body").on("click", "[data-add-project]", function() {
			$scope.project = {
				name: ""
			};
			$scope.error = false;
			$scope.$apply();
		});


		$("body").on("click", "tr", function() {
			if ($(this).find(".dataTables_empty").length) {

			} else {
				$scope.error = false;
				$scope.project = {
					name: $(this).find("[data-name]").html(),
					id: $(this).find("[data-id]").html()
				};
				
				$scope.$apply();
				$("#add-project-modal").modal("show");
			}
		});
	});
});
