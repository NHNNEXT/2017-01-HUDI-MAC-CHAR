
//  RoomListTableViewCell.h
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 14..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RoomListTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *roomId;
@property (weak, nonatomic) IBOutlet UILabel *roomTitle;
@property (weak, nonatomic) IBOutlet UILabel *userCount;

@end
