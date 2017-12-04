//
//  CahtTableViewCell.h
//  HUDI-Mafia
//
//  Created by YongJai on 2017. 4. 19..
//  Copyright © 2017년 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ChatTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *usersNick;
@property (weak, nonatomic) IBOutlet UILabel *usersChat;
@property (weak, nonatomic) IBOutlet UILabel *myNick;
@property (weak, nonatomic) IBOutlet UILabel *myChat;

@end
