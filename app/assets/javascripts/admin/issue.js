app.controller('IssueController', function($scope, $http) {
    $scope.issue = {};
    $scope.error = false;

    $scope.saveIssue = function() {
        $http.post(Routes.saveProject, JSON.stringify($scope.issue)).success(function(data, status) {
            if (!isNaN(Number(data)) && Number(data)!==0) {
				if (isNaN(Number($scope.project.id)))
					$("tbody").append("<tr><td data-id>"+data+"</td><td data-name>"+$scope.project.name+"</td></tr>");
				else
					$("tbody").find("[data-id]").each(function(e) {
						if ($(this).html()===$scope.project.id)
							$(this).parent().find("[data-name]").html($scope.project.name);
					});
				$scope.error = false;
            } else {
				$scope.error = true;
            }
        }).error(function(data, status) {
			$scope.error = true;
        });
    };

    $(function() {
		$("body").on("click","[data-add-issue]", function() {
			$scope.error = false;
			$scope.issue = {
				name: ""
			};
			$scope.$apply();
		});


		$("body").on("click", "tr", function() {
			if ($(this).find(".dataTables_empty").length) {

			} else {
				$scope.error = false;
				$scope.issue = {
					title: $(this).find("[data-title]").html(),
					id: $(this).find("[data-id]").html(),
					status: $(this).find("[data-status]").html(),
					text: $(this).find("[data-text]").html()
				};
				
				$scope.$apply();
				$("#add-issue-modal").modal("show");
			}
		});
	});
});
