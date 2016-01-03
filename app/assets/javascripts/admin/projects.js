app.controller('ProjectController', function($scope, $http) {
    $scope.project = {};
    $scope.error = "";

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
            } else {
				$scope.error = "Couldn't save project";
            }
        }).error(function(data, status) {
			$scope.error = "Couldn't save project";
        });
    };

    $(function() {
		$("[data-add-project]").click(function() {
			$scope.project = {
				name: ""
			};
			$scope.$apply();
		});


		$("tr").click(function() {
			if ($(this).find(".dataTables_empty").length) {

			} else {
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
