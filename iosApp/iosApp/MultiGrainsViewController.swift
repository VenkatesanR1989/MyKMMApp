import Foundation
import UIKit
import shared

final class MultiGrainsViewController: UIViewController {
  @IBOutlet var tableView: UITableView!

  //swiftlint:disable implicitly_unwrapped_optional
  var api: GrainApi!
  //swiftlint:enable implicitly_unwrapped_optional

  var grainList: [Grain] = []

  let cellIdentifier = "cell"

  override func viewDidLoad() {
    super.viewDidLoad()

    api = GrainApi(context: self)

    tableView.dataSource = self
    tableView.delegate = self

    loadList()
  }

  func loadList() {
    api.getGrainList(success: { grains in
      self.grainList = grains
      self.tableView.reloadData()
    }, failure: { error in
      print(error?.description() ?? "")
    })
  }
}
