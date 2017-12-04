//
//  UserCollectionViewCell.h
//  HUDI-Mafia
//
//  Created by YongJai on 16/05/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UserCollectionViewCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UILabel *userReady;
@property (weak, nonatomic) IBOutlet UILabel *userName;
@property (weak, nonatomic) IBOutlet UIImageView *voteTarget;

@end
